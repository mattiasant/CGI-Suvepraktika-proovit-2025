import { useEffect, useState } from "react";
import Flights from "./pages/Flights";

function App() {
    const [flights, setFlights] = useState([]);
    const [search, setSearch] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/flights")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                console.log("Fetched Flights:", data); // Debugging step
                setFlights(data);
            })
            .catch(error => console.error("Error fetching flights:", error));
    }, []);

    const filteredFlights = flights.filter(flight =>
        flight.destination.toLowerCase().includes(search.toLowerCase())
    );

    return (
        <div>
            <h1 className="text-3xl font-bold p-5">Flight Booking App</h1>

            <input
                type="text"
                placeholder="Search by Destination"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                className="p-2 border rounded-md mb-4"
            />

            <Flights flights={filteredFlights} />
            
        </div>
    );
}

export default App;
