import routes from '../routes.json'

export async function getProfile(){
    const token = localStorage.getItem('userToken');
    console.log(token);
    const keysRoute = routes.host + routes.profile;

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