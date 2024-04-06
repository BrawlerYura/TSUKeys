import routes from '../routes.json'

let keysTest = [
    {
        ID:"228", 
        lastInPossession:"'111'", 
        lastInPossessionName:"'Alexey Babanov'", 
        keyNumber:"228"
    },
    {
        ID:"228", 
        lastInPossession:"'111'", 
        lastInPossessionName:"'Alexey Babanov'", 
        keyNumber:"228"
    }
]

export async function getKeys() {
    const token = localStorage.getItem('token');
    console.log(token);
    const keysRoute = routes.host + routes.keys;

    try {
        const response = await fetch(keysRoute, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log(data,  1);
        return data;
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
    }
}