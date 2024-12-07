// functions/bsky.js
import { AtpAgent, AppBskyFeedDefs, AppBskyFeedPost } from '@atproto/api';

const agent = new AtpAgent({ service: 'https://public.api.bsky.app' });

export async function handler(event, context) {
  const { q, limit = 12, lang = 'it' } = JSON.parse(event.body);

  if (!q) {
    return {
      statusCode: 400,
      body: JSON.stringify({ error: "Il parametro 'q' è obbligatorio" })
    };
  }

  try {
    const postsData = await searchPosts(q, limit, lang);
    return {
      statusCode: 200,
      body: JSON.stringify(postsData)
    };
  } catch (error) {
    console.error('Errore nella ricerca di post:', error);
    return {
      statusCode: 500,
      body: JSON.stringify({ error: 'Errore interno del server' })
    };
  }
}

async function searchPosts(q, limit, lang) {
  const profile = await agent.app.bsky.feed.searchPosts({ q, limit, lang });
  return await Promise.all(
    profile.data.posts.map(async (post) => {
      const thread = await agent.app.bsky.feed.getPostThread({ uri: post.uri });
      if (!AppBskyFeedDefs.isThreadViewPost(thread.data.thread)) {
        throw new Error('Expected a thread view post');
      }

      const postDetails = thread.data.thread.post;
      if (!AppBskyFeedPost.isRecord(postDetails.record)) {
        throw new Error('Expected a post with a record');
      }

      const did = postDetails.author.did;
      const handle = postDetails.author.handle;
      const postId = post.uri.split('/').pop();

      return {
        did,
        handle,
        text: postDetails.record.text,
        url: `https://bsky.app/profile/${handle}/post/${postId}`,
      };
    })
  );
}
