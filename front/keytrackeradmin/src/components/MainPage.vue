<script setup>
</script>

<template>
  <div class="center">
    <div class="container">
      <h1>KeyTracker Admin</h1>
      <div>
        <button @click="this.$router.push('/login')">Войти</button>
        <button class="btn-mg-l" @click="this.$router.push('/registration')">Зарегистрироваться</button>
      </div>
    </div>
  </div>
</template>

<style>
.btn-mg-l{
  margin-left: 10px;
}
</style>