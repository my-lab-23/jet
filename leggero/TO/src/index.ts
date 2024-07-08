import express, { Router } from 'express';
import serverless from 'serverless-http';
import { AppDataSource } from "./data-source";
import { User } from "./entity/User";

const api = express();
const router = Router();

router.get('/runCode', async (req, res) => {
  try {
    if (!AppDataSource.isInitialized) {
      await AppDataSource.initialize();
    }

    console.log("Inserting a new user into the database...");
    const user = new User();
    user.firstName = "Timber";
    user.lastName = "Saw";
    user.age = 25;
    await AppDataSource.manager.save(user);
    console.log("Saved a new user with id: " + user.id);

    console.log("Loading users from the database...");
    const users = await AppDataSource.manager.find(User);
    console.log("Loaded users: ", users);

    res.send(users);
  } catch (error) {
    console.log(error);
    res.status(500).send(error);
  }
});

api.use("/api/", router);

export const handler = serverless(api);
