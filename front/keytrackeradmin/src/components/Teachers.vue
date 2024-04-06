<script setup>
import Teacher from './Teacher.vue'
import Navbar from './Navbar.vue'
import { getTeachers } from '../functions/getTeachers'
import { ref, onMounted } from 'vue';

const teachers = ref([]);

onMounted(async () => {
  const fetchedKeys = await getTeachers();
  teachers.value = fetchedKeys;
});
</script>

<template>
    <div class="cock"><Navbar/></div>
    <div class="key-list">
        <Teacher v-for="item in teachers" :ID="item.id" :name="item.name" :job="item.role" :email="item.email"/>
    </div>
</template>

<style>

</style>