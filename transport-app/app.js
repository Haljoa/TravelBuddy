const { createApp } = Vue;

createApp({
    data() {
        return {
            stats: {},
            backendStatus: false
        }
    },
    async mounted() {
        console.log("🚀 Frontend starter...");
        await this.checkBackend();
    },
    methods: {
        async checkBackend() {
            try {
                const response = await fetch('http://localhost:8080/api/stats');
                const data = await response.json();
                this.stats = data;
                this.backendStatus = true;
                console.log("✅ Backend fungerer!", data);
            } catch (error) {
                console.log("❌ Backend ikke tilgjengelig");
                this.stats = { totalJourneys: 0, totalAgencies: 0, totalStops: 0 };
                this.backendStatus = false;
            }
        }
    }
}).mount('#app');