import axios from 'axios';
import log from './logger';

//

export type Event = {
  startDate: Date;
  endDate: Date;
  result: string;
};

//

async function makeRequest(endpoint: string, data: any): Promise<any> {
  let baseUrl: string;

  if (process.env.NETLIFY_DEV === 'true') {
    baseUrl = "http://localhost:3000";
  } else {
    baseUrl = "https://leggero-jannick-beto.netlify.app";
    log.debug(baseUrl);
  }

  try {
    const response = await axios.post(baseUrl + endpoint, data, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    return response;
  } catch (error) {
    throw error;
  }
}

//

export async function insertEvent(event: Event): Promise<number> {
  const response = await makeRequest('/api/insertEvent', event);
  log.debug(response.data.event)
  return response.status;
}

export async function searchEvent(date: string): Promise<any> {
  const response = await makeRequest('/api/searchEvent', { date: date });

  if (response.status === 200) {
    return response;
  } else {
    return response.data.message;
  }
}
