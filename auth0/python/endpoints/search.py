import re
import os

# Define the regular expression for finding Ktor endpoints
ENDPOINT_REGEX = re.compile(r"(get|post)\(\"([^\"]+)\"")

def find_endpoints_in_file(file_path):
    """
    Finds all Ktor endpoints defined in the specified file.

    Args:
        file_path (str): The path to the file to search.

    Returns:
        A list of tuples representing the HTTP method (e.g. "get") and endpoint path (e.g. "/users").
    """
    endpoints = []
    with open(file_path, "r") as file:
        for line in file:
            # Search for the endpoint definition using the regular expression
            match = ENDPOINT_REGEX.search(line)
            if match:
                http_method = match.group(1)
                endpoint_path = match.group(2)
                endpoints.append((http_method, endpoint_path))
    return endpoints

def find_endpoints_in_directory(directory_path):
    """
    Finds all Ktor endpoints defined in the specified directory.

    Args:
        directory_path (str): The path to the directory to search.

    Returns:
        A list of tuples representing the HTTP method (e.g. "get") and endpoint path (e.g. "/users").
    """
    # Ricerca ricorsiva di tutti i file .kt
    endpoints = []
    for root, dirs, files in os.walk(directory_path):
        for file in files:
            if file.endswith(".kt"):
                endpoints.extend(find_endpoints_in_file(os.path.join(root, file)))
    return endpoints

def main():
    # Crea una lista di directory da analizzare
    directories = ["/home/ema/Scrivania/desperado/auth0/src/main/kotlin",
                   "/home/ema/Scrivania/desperado/esperanza/src/main/kotlin"]
    
    # Cancella il file endpoints.txt
    if os.path.exists("endpoints.txt"):
        os.remove("endpoints.txt")

    # Find all endpoints in the specified directory
    endpoints = []
    for directory in directories:
        endpoints.extend(find_endpoints_in_directory(directory))

    # Cancella gli endpoint che contengono "http"
    endpoints = [endpoint for endpoint in endpoints if "http" not in endpoint[1]]

    for endpoint in endpoints:
        # Scrivi gli endpoint su file
        with open("endpoints.txt", "a") as file:
            file.write(endpoint[1])
            file.write("\n")
        print(endpoint[1])

if __name__ == "__main__":
    main()
