# importing the requests library 
import requests 
  
URL = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=driving&language=fr-FR&key=AIzaSyC5i3oXJ3NUAXNR6pzSSSoxyCCBMNg4ffI"
# defining a params dict for the parameters to be sent to the API 

matrix_patients=[ [0, 1, 1, 0],[1,0,0,1], [1,1,0,0],[0,0,1,0] ]
addresses_patients=["Sonchamp France", "Clairefontaine en Yvelines France", "St Arnoult en Yvelines France", "Ponthévrard France" ]


def compute_distance(origins, destinations):
    PARAMS ={}
    str_origins = ""
    for i in range(len(origins)):
        str_origins+=origins[i]+"|"
    PARAMS["origins"] = str_origins
        
    str_destinations = ""
    for i in range(len(destinations)):
        str_destinations+=destinations[i]+"|"
    PARAMS["destinations"] = str_destinations
    
    tab = []
    
    
    r = requests.get(url = URL, params = PARAMS) 
    # extracting data in json format 
    data = r.json() 
    
    print("The destinations are", data["destination_addresses"])
    print("The origins are", data["origin_addresses"], "\n")
    
    
    for i in range(len(origins)):
        for j in range (len(destinations)):
            if (data["origin_addresses"][i]!="" and data["destination_addresses"][j]!=""):
                print("From ", origins[i], "to ", destinations[j])
                print(data['rows'][i]['elements'][j]['distance']['text'])
                print(data['rows'][i]['elements'][j]['duration']['text'])
for i in range(len(matrix_patients)):
    for j in range(len(matrix_patients[0])):
        if (matrix_patients[i][j] == 1):
            compute_distance([addresses_patients[i]], [addresses_patients[j]])
tab