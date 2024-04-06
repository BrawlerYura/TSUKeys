import routes from '../routes.json'

let testApps = [
    {
        ID:"111", 
        Name:"'Alexey Babanov'", 
        keyNumber:"228", 
        keyID:"'228'", 
        time:"'10:35'", 
        type:"'Повторяющаяся'"
    },
    {
        ID:"111", 
        Name:"'Alexey Babanov'", 
        keyNumber:"228", 
        keyID:"'228'", 
        time:"'10:35'", 
        type:"'Повторяющаяся'"
    }
]

export async function getApplications(){
    const token = localStorage.getItem('token');
    console.log(token);
    const applicationsRoute = routes.host + routes.applications;

    try {
        const response = await fetch(applicationsRoute, {
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