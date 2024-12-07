<script lang="ts">
import { defineComponent, ref } from 'vue';
import VueSpeedometer from 'vue-speedometer';

export default defineComponent({
  name: "MySpeed",
  components: {
    VueSpeedometer,
  },
  setup() {
    const speed = ref(0);
    const isAccelerating = ref(false);

    const accelerate = () => {
      if (isAccelerating.value) return;
      isAccelerating.value = true;
      let targetSpeed = Math.floor(Math.random() * 151 + 50);

      const interval = setInterval(() => {
        if (speed.value < targetSpeed) {
          speed.value += 5;
        } else {
          clearInterval(interval);
          decelerate();
        }
      }, 100);
    };

    const decelerate = () => {
      const interval = setInterval(() => {
        if (speed.value > 0) {
          speed.value -= 5;
        } else {
          clearInterval(interval);
          isAccelerating.value = false;
        }
      }, 100);
    };

    return {
      speed,
      accelerate,
    };
  },
});
</script>

<template>
  <button @click="accelerate" class="btn-accelerate">
    Accelerare
  </button>
  <div class="speedometer-container">
    <vue-speedometer
        :value="speed"
        :minValue="0"
        :maxValue="200"
        segments="5"
        needleColor="red"
        startColor="green"
        endColor="orange"
    />
  </div>
</template>

<style scoped>
.speedometer-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  margin-top: 50px;
  width: 300px;
  height: 150px;
}

.btn-accelerate {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #1c1c1c;
  color: #f1f1f1;
  border: 1px solid #333;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.btn-accelerate:hover {
  background-color: #333;
  color: #fff;
}
</style>
