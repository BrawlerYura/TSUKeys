<script setup>
import { ref, onMounted } from 'vue';
import routes from '../routes.json'
const props = defineProps(['ID','name','email','isActive'])

const active = ref(props.isActive)

async function activate(){
    const token = localStorage.getItem('token');
    console.log(token);
    const keysRoute = routes.host + routes.activateDean + '?id=' + props.ID + '&isActive=' + !active.value;

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
        else{
            active.value = !active.value
        }
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
    }
}
</script>

<template>
    <div class="key-card">
        <div>Название: <a>{{ name }}</a></div>
        <div>Email: {{ email }}</div>
        <button v-if="!active" @click="activate()">Активировать</button>
        <button v-else @click="activate()">Деактивировать</button>
    </div>
</template>

<style>
.key-list{
    display: flex;
    flex-direction: column; 
    justify-content: left;
    max-width: 15vw;
}

.btn-mg-b{
  padding-bottom: 10px;
}
</style>