import requests
import sys

def create_html_page(endpoints):
    """
    Creates an HTML page with links to the specified endpoints.

    Args:
        endpoints (list): A list of endpoint paths.

    Returns:
        A string containing the HTML code for the page.
    """
    # Create the HTML page header
    html = "<html><head><title>Active Endpoints</title></head><body>"

    # Add a link to each endpoint
    for endpoint in endpoints:
        html += f"<a href='{endpoint}'>{endpoint}</a><br>"

    # Close the HTML page
    html += "</body></html>"

    return html

# Check if valid domain name is provided as command line argument
if len(sys.argv) != 2:
    print("Usage: python3 endpoint_scanner.py domain.com")
    sys.exit()

# Define common endpoint paths to scan
# Leggi gli endpoint dal file endpoints.txt
endpoints = []
with open("endpoints.txt", "r") as f:
    for line in f:
        endpoints.append(line.strip())

alive = []

# Loop through each endpoint and send GET request
for endpoint in endpoints:
    url = f"https://{sys.argv[1]}{endpoint}"
    response = requests.get(url)
    if response.status_code == 200:
        print(f"[+] Found endpoint: {url}")
        alive.append(url)
    else:
        print(f"[-] Endpoint not found: {url}")
        
# Create HTML page with links to active endpoints
html = create_html_page(alive)
# Write HTML page to file
with open("endpoints.html", "w") as f:
    f.write(html)
