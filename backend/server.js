import express from 'express';
import cors from 'cors';
import { MongoClient } from 'mongodb';

const app = express();
const PORT = 8080;

// Middleware
app.use(cors());
app.use(express.json());

// MongoDB connection - BRUK DIN CONNECTION STRING
const MONGODB_URI = "mongodb+srv://minhbaopokemon_db_user:pooplord123@cluster0.dikzcnh.mongodb.net/database1?retryWrites=true&w=majority";
const client = new MongoClient(MONGODB_URI);

// Connect to MongoDB
async function connectToMongoDB() {
    try {
        await client.connect();
        console.log('✅ Connected to MongoDB');
        return client.db('database1');
    } catch (error) {
        console.error('❌ MongoDB connection error:', error);
        throw error;
    }
}

// Health check
app.get('/', (req, res) => {
    res.json({ message: '🚀 TravelBuddy Backend is running!' });
});

// Get stats
app.get('/api/stats', async (req, res) => {
    try {
        const db = await connectToMongoDB();
        
        const journeysCount = await db.collection('journeys').countDocuments();
        const agenciesCount = await db.collection('agencies').countDocuments();
        const stopsCount = await db.collection('stops').countDocuments();
        
        const busCount = await db.collection('journeys').countDocuments({ 
            routeId: { $regex: '^BUS_' } 
        });
        const trainCount = await db.collection('journeys').countDocuments({ 
            routeId: { $regex: '^TRAIN_' } 
        });
        const boatCount = await db.collection('journeys').countDocuments({ 
            routeId: { $regex: '^BOAT_' } 
        });

        res.json({
            totalJourneys: journeysCount,
            totalAgencies: agenciesCount,
            totalStops: stopsCount,
            busJourneys: busCount,
            trainJourneys: trainCount,
            boatJourneys: boatCount
        });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Get journeys
app.get('/api/journeys', async (req, res) => {
    try {
        const db = await connectToMongoDB();
        const journeys = await db.collection('journeys').find().limit(20).toArray();
        res.json(journeys);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Get stops
app.get('/api/stops', async (req, res) => {
    try {
        const db = await connectToMongoDB();
        const stops = await db.collection('stops').find().limit(50).toArray();
        res.json(stops);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Start server
app.listen(PORT, () => {
    console.log(`🚀 Backend server running on http://localhost:${PORT}`);
    console.log(`📊 API: http://localhost:${PORT}/api/stats`);
});