const API_BASE = 'http://localhost:8080/api/trips';

// Søk etter spesifikk trip
async function searchTrip() {
    const routeId = document.getElementById('searchInput').value.trim();
    const resultDiv = document.getElementById('searchResult');
    
    if (!routeId) {
        resultDiv.innerHTML = '<p class="error">Vennligst skriv inn en Route ID</p>';
        return;
    }
    
    resultDiv.innerHTML = '<p class="loading">Søker...</p>';
    
    try {
        const response = await fetch(`${API_BASE}/${routeId}`);
        
        if (!response.ok) {
            throw new Error('Rute ikke funnet');
        }
        
        const trip = await response.json();
        displayTripResult(trip, resultDiv);
        
    } catch (error) {
        resultDiv.innerHTML = `
            <div class="error">
                <p><strong>Feil:</strong> ${error.message}</p>
                <p>Prøv en annen Route ID</p>
            </div>
        `;
    }
}

// Hjelpefunksjon for crowdedness badge
function getCrowdednessBadge(level) {
    if (level <= 3) return 'crowdedness-low';
    if (level <= 7) return 'crowdedness-medium';
    return 'crowdedness-high';
}

function getCrowdednessText(level) {
    if (level <= 3) return 'Lav';
    if (level <= 7) return 'Middels';
    return 'Høy';
}

// Vis søkeresultat
function displayTripResult(trip, container) {
    const crowdednessClass = getCrowdednessBadge(trip.crowdednessLevel);
    const crowdednessText = getCrowdednessText(trip.crowdednessLevel);
    
    container.innerHTML = `
        <div class="trip-card">
            <div class="trip-header">
                <span class="route-id">${trip.routeId}</span>
                <span class="crowdedness-badge ${crowdednessClass}">
                    Travlhet: ${crowdednessText} (${trip.crowdednessLevel})
                </span>
            </div>
            
            <div class="trip-details">
                <div class="detail-item">⏱️ <strong>Total varighet:</strong> ${trip.totalRouteDuration} minutter</div>
                <div class="detail-item">👥 <strong>Travlhetsnivå:</strong> ${trip.crowdednessLevel}/10</div>
            </div>
            
            ${trip.durationsBetweenStops && trip.durationsBetweenStops.length > 0 ? `
                <div class="durations-list">
                    <h4>⏰ Varighet mellom stopp:</h4>
                    <div>${trip.durationsBetweenStops.join(' → ')} minutter</div>
                </div>
            ` : ''}
            
            ${trip.deviations ? `
                <div class="deviations">
                    <h4>⚠️ Avvik:</h4>
                    <div>${trip.deviations}</div>
                </div>
            ` : ''}
        </div>
    `;
}

// Legg til ny trip
async function addNewTrip() {
    const durationsText = document.getElementById('newDurations').value.trim();
    const durationsBetweenStops = durationsText ? 
        durationsText.split(',').map(num => parseInt(num.trim())).filter(num => !isNaN(num)) : 
        [];
    
    const newTrip = {
        routeId: document.getElementById('newRouteId').value.trim(),
        totalRouteDuration: parseInt(document.getElementById('newTotalDuration').value) || 0,
        crowdednessLevel: parseFloat(document.getElementById('newCrowdedness').value) || 0,
        deviations: document.getElementById('newDeviations').value.trim() || null,
        durationsBetweenStops: durationsBetweenStops
    };
    
    if (!newTrip.routeId || !newTrip.totalRouteDuration) {
        alert('Vennligst fyll ut Route ID og Total varighet');
        return;
    }
    
    if (newTrip.crowdednessLevel < 0 || newTrip.crowdednessLevel > 10) {
        alert('Travlhetsnivå må være mellom 0 og 10');
        return;
    }
    
    try {
        const response = await fetch(API_BASE, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newTrip)
        });
        
        const result = await response.text();
        
        document.getElementById('searchResult').innerHTML = `
            <div class="success">
                <p><strong>Suksess!</strong> ${result}</p>
            </div>
        `;
        
        // Reset skjema
        document.getElementById('newRouteId').value = '';
        document.getElementById('newTotalDuration').value = '';
        document.getElementById('newCrowdedness').value = '';
        document.getElementById('newDeviations').value = '';
        document.getElementById('newDurations').value = '';
        
        // Oppdater listen
        loadAllTrips();
        
    } catch (error) {
        document.getElementById('searchResult').innerHTML = `
            <div class="error">
                <p><strong>Feil ved lagring:</strong> ${error.message}</p>
            </div>
        `;
    }
}

// Last alle trips
async function loadAllTrips() {
    const container = document.getElementById('allTripsList');
    container.innerHTML = '<p class="loading">Laster alle ruter...</p>';
    
    try {
        const response = await fetch(API_BASE);
        const trips = await response.json();
        
        if (trips.length === 0) {
            container.innerHTML = '<p class="placeholder">Ingen ruter funnet. Legg til noen!</p>';
            return;
        }
        
        container.innerHTML = trips.map(trip => {
            const crowdednessClass = getCrowdednessBadge(trip.crowdednessLevel);
            const crowdednessText = getCrowdednessText(trip.crowdednessLevel);
            
            return `
                <div class="trip-card">
                    <div class="trip-header">
                        <span class="route-id">${trip.routeId}</span>
                        <span class="crowdedness-badge ${crowdednessClass}">
                            ${crowdednessText}
                        </span>
                    </div>
                    
                    <div class="trip-details">
                        <div class="detail-item">⏱️ <strong>Varighet:</strong> ${trip.totalRouteDuration} min</div>
                        <div class="detail-item">👥 <strong>Travlhet:</strong> ${trip.crowdednessLevel}/10</div>
                    </div>
                    
                    ${trip.durationsBetweenStops && trip.durationsBetweenStops.length > 0 ? `
                        <div style="font-size: 0.9em; color: #666;">
                            <strong>Stopp:</strong> ${trip.durationsBetweenStops.length} stopp
                        </div>
                    ` : ''}
                    
                    ${trip.deviations ? `
                        <div style="color: #e74c3c; font-size: 0.9em; margin-top: 8px;">
                            ⚠️ ${trip.deviations}
                        </div>
                    ` : ''}
                </div>
            `;
        }).join('');
        
    } catch (error) {
        container.innerHTML = `
            <div class="error">
                <p><strong>Feil ved lasting:</strong> ${error.message}</p>
                <p>Sjekk at backend kjører på localhost:8080</p>
            </div>
        `;
    }
}

// Søk når bruker trykker Enter
document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        searchTrip();
    }
});

// Last alle trips når siden loader
document.addEventListener('DOMContentLoaded', function() {
    loadAllTrips();
});