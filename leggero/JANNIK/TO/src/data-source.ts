import "reflect-metadata"
import { DataSource } from "typeorm"
import { Event } from "./entity/Event"

export const AppDataSource = new DataSource({
    type: "postgres",
    host: process.env.PG_HOST,
    port: +process.env.PG_PORT,
    username: process.env.PG_USER,
    password: process.env.PG_PASSWORD,
    database: process.env.PG_DATABASE,
    synchronize: false,
    //logging: true, // Abilita i log
    entities: [Event],
    migrations: [],
    subscribers: [],
})
