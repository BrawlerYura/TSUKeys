import routes from '../routes.json'

export async function getDeans(){
    const keysRoute = routes.host + routes.adminDeans;
    const token = localStorage.getItem('token');

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
        return data;
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
    }
}