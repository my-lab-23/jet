import express from 'express';
import cors from 'cors';
import fs from 'fs';
import rateLimit from 'express-rate-limit'; // Middleware per il rate limiting
import { AtpAgent, AppBskyFeedDefs, AppBskyFeedPost } from '@atproto/api';

const agent = new AtpAgent({
  service: "https://public.api.bsky.app",
});

const app = express();
const port = 3009;

app.use(cors()); // Abilita CORS per tutte le origini
app.use(express.json());

// Configura il rate limiter
const searchLimiter = rateLimit({
  windowMs: 15 * 60 * 1000, // Intervallo di 15 minuti
  max: 10, // Max 10 richieste per IP
  message: { error: "Troppe richieste, riprova più tardi." },
  standardHeaders: true, // Invia intestazioni standard sulle limitazioni
  legacyHeaders: false, // Disabilita intestazioni legacy
});

// Applica il rate limiter solo all'endpoint /ex0/search-posts
app.post('/ex0/search-posts', searchLimiter, async (req, res) => {
  const { q, limit = 12, lang = "it" } = req.body;

  if (!q) {
    return res.status(400).json({ error: "Query parameter 'q' is required" });
  }

  try {
    const profile = await agent.app.bsky.feed.searchPosts({
      q: q,
      limit: limit,
      lang: lang,
    });

    const postsData = await Promise.all(profile.data.posts.map(async (post) => {
      const thread = await agent.app.bsky.feed.getPostThread({ uri: post.uri });

      if (!AppBskyFeedDefs.isThreadViewPost(thread.data.thread)) {
        throw new Error("Expected a thread view post");
      }

      const postDetails = thread.data.thread.post;

      if (!AppBskyFeedPost.isRecord(postDetails.record)) {
        throw new Error("Expected a post with a record");
      }

      const did = postDetails.author.did;
      const handle = postDetails.author.handle;
      const postId = post.uri.split('/').pop();

      return {
        did: did,
        handle: handle,
        text: postDetails.record.text,
        url: `https://bsky.app/profile/${handle}/post/${postId}`,
      };
    }));

    const timestamp = new Date().toISOString().replace(/:/g, "-");
    const filePath = `/home/ema/Documenti/Bsky/${q}_${timestamp}.json`;

    fs.writeFileSync(filePath, JSON.stringify(postsData, null, 2));
    console.log(`File saved to ${filePath}`);

    res.status(200).json(postsData);

  } catch (error) {
    console.error("Error searching posts:", error);
    res.status(500).json({ error: "Internal server error" });
  }
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
