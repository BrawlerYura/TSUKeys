<script setup>
import AdminNavbar from './AdminNavbar.vue'
import { getDeans } from '../functions/getDeans.js'
import { ref, onMounted } from 'vue'
import AdminDean from './AdminDean.vue'

const deans = ref([])

onMounted(async () => {
  const fetchedKeys = await getDeans();
  deans.value = fetchedKeys;
});

</script>

<template>
    <div class="cock"><AdminNavbar/></div>
    <div class="key-list">
        <AdminDean v-for="dean in deans" :ID="dean.id" :name="dean.name" :isActive="dean.isActive" :email="dean.email"/>
    </div>
</template>