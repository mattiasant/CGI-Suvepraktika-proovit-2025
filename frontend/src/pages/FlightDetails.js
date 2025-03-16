import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

function FlightDetails() {
    const { id } = useParams();
    const [flight, setFlight] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [groupSize, setGroupSize] = useState(1); // 🔹 User input for seat grouping
    const [selectedSeat, setSelectedSeat] = useState(null); // 🔹 Track selected seat

    useEffect(() => {
        fetch(`http://localhost:8080/flights/${id}`)
            .then(response => response.json())
            .then(data => {
                console.log("✅ Full Flight Data:", data);
                setFlight(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("❌ Error fetching flight details:", error);
                setError(error.message);
                setLoading(false);
            });
    }, [id]);

    if (loading) return <p>Loading flight details...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!flight || !flight.seats) return <p>No flight data available.</p>;

    // 🔹 Handle user input for grouping seats
    const handleGroupInput = (e) => {
        const value = parseInt(e.target.value, 10);
        if (!isNaN(value) && value > 0) {
            setGroupSize(value);
        }
    };

    // 🔹 Handle seat selection
    const handleSeatClick = (seat) => {
        if (!seat.occupied) {
            setSelectedSeat(seat);
        }
    };

    // 🔹 Handle seat booking (update backend & UI)
    const handleBookSeat = () => {
        if (!selectedSeat) return;

        fetch(`http://localhost:8080/flights/${id}/select-seat`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ seatId: selectedSeat.seatNumber }) // ✅ Ensure seatId matches backend format
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.error || "Booking failed");
                    });
                }
                return response.text();
            })
            .then(message => {
                alert("✅ " + message);

                // ✅ Update seat status in UI
                setFlight(prevFlight => ({
                    ...prevFlight,
                    seats: prevFlight.seats.map(seat =>
                        seat.seatNumber === selectedSeat.seatNumber ? { ...seat, occupied: true } : seat
                    )
                }));

                setSelectedSeat(null); // Clear selection
            })
            .catch(error => alert("❌ Error booking seat: " + error.message));
    };

    const organizeSeatsIntoRows = (seats) => {
        const rows = {};
        seats.forEach(seat => {
            const rowNumber = seat.seatNumber.match(/\d+/)[0];
            if (!rows[rowNumber]) rows[rowNumber] = [];
            rows[rowNumber].push(seat);
        });

        Object.keys(rows).forEach(row => {
            rows[row].sort((a, b) => a.seatNumber.localeCompare(b.seatNumber));
        });

        return rows;
    };

    const seatRows = organizeSeatsIntoRows(flight.seats);

    return (
        <div>
            <h2>{flight.destination}</h2>

            {/* 🔹 Legend for seat colors */}
            <div style={{ marginBottom: "15px", fontSize: "14px" }}>
                <strong>Legend:</strong>
                <div style={{ display: "flex", gap: "15px", marginTop: "5px" }}>
                    <div style={{ border: "3px solid red", padding: "5px" }}>❌ Taken</div>
                    <div style={{ border: "3px solid green", padding: "5px" }}>✅ Available</div>
                    <div style={{ border: "3px solid blue", padding: "5px" }}>✈️ First Class</div>
                    <div style={{ border: "3px solid orange", padding: "5px" }}>🌅 Window Seat</div>
                    <div style={{ border: "3px solid purple", padding: "5px" }}>🚪 Near Exit</div>
                    <div style={{ border: "3px solid yellow", padding: "5px" }}>
                        🟡 Group of {groupSize} Seats (if found)
                    </div>
                </div>
            </div>

            <div style={{ marginBottom: "15px" }}>
                <label>Find group of seats: </label>
                <input
                    type="number"
                    value={groupSize}
                    onChange={handleGroupInput}
                    min="1"
                    style={{
                        width: "50px",
                        marginLeft: "10px",
                        padding: "5px",
                        border: "1px solid black"
                    }}
                />
            </div>

            <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                {Object.keys(seatRows).map((rowNumber) => (
                    <div key={rowNumber} style={{ display: "flex", gap: "10px" }}>
                        {seatRows[rowNumber].map(seat => {
                            let borderColor = "black";

                            if (seat.occupied) borderColor = "red"; // ❌ Taken
                            else borderColor = "green"; // ✅ Available
                            if (seat.firstClass) borderColor = "blue"; // ✈️ First Class
                            if (seat.window) borderColor = "orange"; // 🌅 Window Seat
                            if (seat.nearExit) borderColor = "purple"; // 🚪 Near Exit
                            if (seat === selectedSeat) borderColor = "gold"; // ⭐ Highlight selected seat

                            return (
                                <button
                                    key={seat.seatNumber}
                                    onClick={() => handleSeatClick(seat)}
                                    style={{
                                        padding: "10px",
                                        fontWeight: "bold",
                                        fontSize: "16px",
                                        border: `3px solid ${borderColor}`,
                                        backgroundColor: seat.occupied ? "red" : "white",
                                        color: seat.occupied ? "white" : "black",
                                        cursor: seat.occupied ? "not-allowed" : "pointer",
                                        borderRadius: "5px"
                                    }}
                                    disabled={seat.occupied}
                                >
                                    {seat.seatNumber}
                                </button>
                            );
                        })}
                    </div>
                ))}
            </div>

            {selectedSeat && (
                <div style={{ marginTop: "15px" }}>
                    <h3>Selected Seat: {selectedSeat.seatNumber}</h3>
                    <button
                        onClick={handleBookSeat}
                        style={{
                            padding: "10px 20px",
                            fontSize: "16px",
                            fontWeight: "bold",
                            color: "white",
                            backgroundColor: "blue",
                            border: "none",
                            borderRadius: "5px",
                            cursor: "pointer"
                        }}
                    >
                        Book Seat
                    </button>
                </div>
            )}

            <Link to="/" style={{ display: "block", marginTop: "20px" }}>Back</Link>
        </div>
    );
}

export default FlightDetails;
