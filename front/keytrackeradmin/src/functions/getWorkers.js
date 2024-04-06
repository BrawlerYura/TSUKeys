import routes from '../routes.json'

export async function getWorkers(){
    const token = localStorage.getItem('token');
    console.log(token);
    const teachersRoute = routes.host + routes.workers;

    try {
        const response = await fetch(teachersRoute, {
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