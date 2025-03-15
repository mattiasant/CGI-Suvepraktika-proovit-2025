import { Link } from "react-router-dom";

function Flights({ flights }) {
    if (!flights || flights.length === 0) {
        return (
            <div className="p-5">
                <h2 className="text-2xl mb-4">No Flights Available</h2>
                <p>There are no flights matching your criteria.</p>
            </div>
        );
    }

    return (
        <div className="p-5">
            <h2 className="text-2xl mb-4">Available Flights</h2>
            <ul>
                {flights.map((flight) => (
                    <li key={flight.id} className="cursor-pointer">
                        <Link to={`/flights/${flight.id}`} className="text-blue-600 hover:underline">
                            ✈️ {flight.destination} - {flight.date} - {flight.flightTime} - {flight.price}€
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Flights;
