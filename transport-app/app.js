const { createApp } = Vue;

createApp({
    data() {
        return {
            API_BASE: 'http://localhost:8080/api',
            stats: {},
            journeys: [],
            stops: [],
            filteredStops: [],
            searchQuery: '',
            loading: {
                stops: false
            },
            error: {
                stops: ''
            },
            backendStatus: false
        }
    },
    async mounted() {
        console.log("🚀 Frontend starter...");
        await this.loadData();
    },
    methods: {
        async loadData() {
            try {
                // Load stats
                const statsResponse = await fetch(`${this.API_BASE}/stats`);
                this.stats = await statsResponse.json();
                
                // Load journeys
                const journeysResponse = await fetch(`${this.API_BASE}/journeys`);
                this.journeys = await journeysResponse.json();
                console.log("Journeys loaded:", this.journeys);
                
                // Load stops
                const stopsResponse = await fetch(`${this.API_BASE}/stops`);
                this.stops = await stopsResponse.json();
                this.filteredStops = this.stops;
                
                this.backendStatus = true;
                console.log("✅ All data loaded from backend!");
                
            } catch (error) {
                console.log("❌ Could not connect to backend:", error);
                this.backendStatus = false;
            }
        },

        loadAllJourneys() {
            // Reload all data to reset filters
            this.loadData();
        },

        filterJourneys(type) {
            
            if (type === 'ALL') {
                this.loadData();
            } else {
                // Filter existing journeys by type
                const filtered = this.journeys.filter(item => 
                    item.journey.routeId.startsWith(type)
                );
                this.journeys = filtered;
            }
        },

        searchStops() {
            if (!this.searchQuery.trim()) {
                this.filteredStops = [...this.stops];
                return;
            }
            
            const query = this.searchQuery.toLowerCase();
            this.filteredStops = this.stops.filter(stop => 
                (stop.stop_name && stop.stop_name.toLowerCase().includes(query)) ||
                (stop.city && stop.city.toLowerCase().includes(query)) ||
                (stop.stop_id && stop.stop_id.toLowerCase().includes(query))
            );
        }
    }
}).mount('#app');