import $ from 'jquery';

let baseUrl = import.meta.env.VITE_BACKEND_URL;

export function fetchDataForGraph(query: string, successCallback: (data: any) => void, errorCallback: (jqXHR: any) => void) {
    $.ajax({
        url: baseUrl + '/api/news',
        method: 'POST',
        contentType: 'text/plain',
        data: query,
        success: successCallback,
        error: errorCallback
    });
}

export function fetchDataForLinks(day: string, successCallback: (response: any) => void, errorCallback: (jqXHR: any) => void) {
    $.ajax({
        url: baseUrl + '/api/day',
        method: 'POST',
        contentType: 'text/plain',
        data: day,
        success: successCallback,
        error: errorCallback
    });
}
