<template>
  <transition name="fade">
    <router-view></router-view>
  </transition>
</template>

<script>
  export default {
  }
</script>
<style>
.shenglue{
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}
</style>