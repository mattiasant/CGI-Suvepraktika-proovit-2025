import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useEffect, useState } from "react";
import Flights from "./pages/Flights";
import FlightDetails from "./pages/FlightDetails";

function App() {
    const [flights, setFlights] = useState([]);
    const [search, setSearch] = useState("");
    const [selectedDate, setSelectedDate] = useState("");
    const [minPrice, setMinPrice] = useState("");
    const [maxPrice, setMaxPrice] = useState("");
    const [sortBy, setSortBy] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/flights")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                console.log("Fetched Flights:", data);
                setFlights(data);
            })
            .catch(error => console.error("Error fetching flights:", error));
    }, []);

    // ✅ Apply Filters
    const filteredFlights = flights
        .filter(flight =>
            flight.destination.toLowerCase().includes(search.toLowerCase()) &&
            (selectedDate === "" || flight.date === selectedDate) &&
            (minPrice === "" || flight.price >= Number(minPrice)) &&
            (maxPrice === "" || flight.price <= Number(maxPrice))
        )
        .sort((a, b) => {
            if (sortBy === "price") return a.price - b.price;
            if (sortBy === "time") return a.flightTime.localeCompare(b.flightTime);
            return 0;
        });

    return (
        <Router>
            <div className="p-5">
                <h1 className="text-3xl font-bold mb-4">Flight Booking App</h1>

                <div className="flex gap-2 mb-4">
                    <input
                        type="text"
                        placeholder="Search Destination"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        className="p-2 border rounded-md"
                    />

                    <input
                        type="date"
                        value={selectedDate}
                        onChange={(e) => setSelectedDate(e.target.value)}
                        className="p-2 border rounded-md"
                    />

                    <input
                        type="number"
                        placeholder="Min Price"
                        value={minPrice}
                        onChange={(e) => setMinPrice(e.target.value)}
                        className="p-2 border rounded-md"
                    />
                    <input
                        type="number"
                        placeholder="Max Price"
                        value={maxPrice}
                        onChange={(e) => setMaxPrice(e.target.value)}
                        className="p-2 border rounded-md"
                    />

                    <select
                        value={sortBy}
                        onChange={(e) => setSortBy(e.target.value)}
                        className="p-2 border rounded-md"
                    >
                        <option value="">Sort By</option>
                        <option value="price">Price (Low to High)</option>
                        <option value="time">Time (Earliest to Latest)</option>
                    </select>
                </div>

                <Routes>
                    <Route path="/" element={<Flights flights={filteredFlights} />} />

                    <Route path="/flights/:id" element={<FlightDetails />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
