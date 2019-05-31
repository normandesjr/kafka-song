<template>
  <div class="winner-box">
    <div class="winner-score">
      <span>{{twitterPosition + 1}}</span>
    </div>
    <div class="winner-name">
      <div class="winner-img">
        <TwitterBird :fill="twitterPosition == 0 ? '#FFFFFF' : '#0DBBF7'"/>
      </div>
      <span>@{{twitterName}}</span>
    </div>
    <div class="winner-time">
      <span>{{twitterTime}}</span>
    </div>
  </div>
</template>

<script>
import TwitterBird from "./TwitterBird";

export default {
  name: "Winner",
  components: {
    TwitterBird
  },
  props: {
    twitterName: String,
    twitterTime: String,
    twitterPosition: Number
  }
};
</script>

<style scoped>
.winner-box {
  background: white;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.16);
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  border-radius: 100px;
  width: 600px;
  height: 85px;
  margin: 20px 0 0 0;
  padding: 0px;
}

.winner-box span {
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.winner-score {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: white;
  color: #10b6f6;
  border-radius: 100%;
  width: 72px;
  height: 72px;
  font-size: 33px;
  font-weight: 600;
  flex-shrink: 0;
  margin-left: 5px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.16);
}

.winner-name {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  min-width: 400px;
  margin-left: 20px;
  font-size: 26.5px;
  color: #1da1f2;
}

ul :first-child .winner-box {
  background: linear-gradient(#08c1f7, #1da1f2);
  box-shadow: none;
}

ul :first-child .winner-box .winner-name {
  color: white;
}

.winner-time {
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-shrink: 0;
  color: #117fbc;
  font-size: 18px;
  background: white;
  border-radius: 100px;
  width: 270px;
  height: 53px;
  position: relative;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.25);
}

.winner-img {
  display: flex;
  padding-right: 10px;
}
</style>


