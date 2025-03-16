import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

function FlightDetails() {
    const { id } = useParams();
    const [flight, setFlight] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/flights/${id}`)
            .then(response => response.json())
            .then(data => {
                console.log("Fetched Flight Details:", data);

                if (data.seats) {
                    data.seats = data.seats.map(seat => ({
                        ...seat,
                        isOccupied: seat.isOccupied ?? seat.occupied ?? false
                    }));
                }

                setFlight(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Error fetching flight details:", error);
                setError(error.message);
                setLoading(false);
            });
    }, [id]);

    const handleSeatSelection = (seatId) => {
        fetch(`http://localhost:8080/flights/${id}/select-seat`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ seatId }),
        })
            .then(response => response.text())
            .then(message => {
                alert(message);
                setFlight(prevFlight => ({
                    ...prevFlight,
                    seats: prevFlight.seats.map(seat =>
                        seat.id === seatId ? { ...seat, isOccupied: true } : seat
                    )
                }));
            })
            .catch(error => console.error("Error booking seat:", error));
    };

    if (loading) return <p>Loading flight details...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!flight || !flight.seats) return <p>No flight data available.</p>;

    const seatRows = {};
    flight.seats.forEach(seat => {
        const rowNumber = seat.seatNumber.match(/\d+/)[0]; // Extract row number
        if (!seatRows[rowNumber]) {
            seatRows[rowNumber] = [];
        }
        seatRows[rowNumber].push(seat);
    });

    return (
        <div style={{ padding: "20px" }}>
            <h2>{flight.destination}</h2>

            <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                {Object.keys(seatRows).map(rowNumber => (
                    <div key={rowNumber} style={{ display: "flex", gap: "15px" }}>
                        <span style={{ fontWeight: "bold" }}>Row {rowNumber}:</span>
                        {seatRows[rowNumber].map(seat => (
                            <strong
                                key={seat.id}
                                onClick={() => !seat.isOccupied && handleSeatSelection(seat.id)}
                                style={{
                                    color: seat.isOccupied ? "red" : "green",
                                    fontSize: "16px",
                                    cursor: seat.isOccupied ? "default" : "pointer",
                                    textDecoration: seat.isOccupied ? "line-through" : "none"
                                }}
                            >
                                {seat.seatNumber}
                            </strong>
                        ))}
                    </div>
                ))}
            </div>

            <Link to="/" style={{ display: "block", marginTop: "10px" }}>Back</Link>
        </div>
    );
}

export default FlightDetails;
