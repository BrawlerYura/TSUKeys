<script setup>
import { ref, onMounted } from 'vue';
import { getAssignedUsers } from '../functions/getAssignedUsers.js';
import routes from '../routes.json'
const emit = defineEmits(['submit'])
const workerCreationRoute = routes.host + routes.assignWorker
const token = localStorage.getItem('token');

let userName = ""
let userId = ""
const listId = 1

const users = ref([]);
const isLoaded = ref(0);

function createWorker(){
    console.log(users.value[0])
    console.log(userName)

    const parts = userName.split(" ");
    console.log(parts[1])

    const dude = users.value.filter(user => user.email == parts[parts.length-1])
    console.log(dude[0].id)

    fetch(workerCreationRoute + '?userId=' + dude[0].id + '&isAssign=' + true, {
                method: 'POST', 
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
    emit('submit')
}

onMounted(async () => {
  const fetchedUsers = await getAssignedUsers();
  users.value = fetchedUsers;
  users.value = users.value.filter(user => !user.isDeanWorker);
  isLoaded.value = 1;
});
</script>

<template>
    <div class="key-card" v-if="isLoaded==1">
        <input type="text" :list="listId" v-model="userName" class="input-card">
            <datalist :id="listId" :key="userName">
                <option v-for="user in users" :key="user.ID" :value="user.ID">{{ user.name }} {{ user.email }}</option>
            </datalist>
        <button @click="createWorker" class="btn-pd-b">Создать</button>
    </div>
</template>

<style>
.input-card{
    width: 10vw;
    margin-top: 10px;
}

.btn-pd-b{
    margin-bottom: 10px;
}

.warning{
    margin: 10px;
    padding-left: 5px;
    padding-right: 5px;
    background-color: #faa405;
    color: black;
    border-radius:  10px;
}
</style>