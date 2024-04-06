<script setup>
import Navbar from './Navbar.vue';
import Worker from './Worker.vue'
import WorkerAddition from './WorkerAddition.vue'
import { getWorkers } from '../functions/getWorkers.js';
import { ref, onMounted, reactive } from 'vue';

const workers = ref([]);

async function refresh(){
  console.log('emitted')
  const fetchedKeys = await getWorkers();
  workers.value=[];
  fetchedKeys.forEach(item => {
    workers.value.push(item); // Add items to the array
  });
  console.log(workers.value)
}

onMounted(async () => {
    const fetchedKeys = await getWorkers();
    workers.value = fetchedKeys;
});
</script>

<template>
    <div class="cock"><Navbar/></div>
    <div class="key-list">
        <Worker v-for="item in workers" :ID="item.id" :name="item.name" :email="item.email" :key="item.id"/>
        <WorkerAddition @submit="refresh()"/>
    </div>
</template>