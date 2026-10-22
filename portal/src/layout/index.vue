<script setup>
import Header from '../components/Header.vue' 
import Footer from '../components/Footer.vue' 
</script>

<template>
  <div class="min-h-screen flex flex-col font-sans text-slate-800 bg-slate-50">
    <Header class="fixed top-0 left-0 w-full z-50" />

    <main class="flex-grow pt-0"> 
      <router-view />
    </main>

    <Footer />
  </div>
</template>