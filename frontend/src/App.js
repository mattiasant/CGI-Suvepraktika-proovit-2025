import { useEffect, useState } from "react";

function App() {
    const [flights, setFlights] = useState([]);
  useEffect(() => {
      fetch("http://localhost:8080/flights")
          .then(response => response.json())
          .then(data => setFlights(data))
          .catch(error => console.error("Error fetching flights:", error));
  }, []);

  return (
      <div>
          <h1>Flights</h1>
          {flights.length > 0 ? (
              <ul>
                  {flights.map((flight, index) => (
                      <li key={index}>{flight.name || `Flight ${index + 1}`}</li>
                  ))}
              </ul>
          ) : (
              <p>No flights found.</p>
          )}
      </div>
  );
}

export default App;
