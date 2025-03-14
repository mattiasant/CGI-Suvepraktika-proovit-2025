import {Link, useParams} from "react-router-dom";
import { useState, useEffect } from "react";

function FlightDetails() {
    const { id } = useParams();  // Extract the flight id from the URL
    const [flight, setFlight] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/flights/${id}`)
            .then(response => response.json())
            .then(data => setFlight(data))
            .catch(error => console.error("Error fetching flight details:", error));
    }, [id]);

    if (!flight) return <div>Loading...</div>;

    return (
        <div className="p-5">
            <h2 className="text-2xl mb-4">Flight Details</h2>
            <div className="mb-4">
                <strong>Flight Number:</strong> {flight.id}
            </div>
            <div className="mb-4">
                <strong>Destination:</strong> {flight.destination}
            </div>
            <div className="mb-4">
                <strong>Departure Time:</strong> {flight.departureTime}
            </div>
            <div className="mb-4">
                <strong>Price:</strong> ${flight.price}
            </div>
            <Link to="/" className="text-blue-500">Back to Flights</Link>
        </div>
    );
}

export default FlightDetails;
