<style lang="scss">
/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
@import "uview-ui/index.scss";
</style>

<script>
export default {
  globalData: {
    token: "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJS8gqOjNQNDXYNUtJRSq0oULIyNDM2tzAwsDQ10FEqLU4t8kwBqjI0MbUwNDKwNDM1M7M0NDIyMLFUgkj7JeamghRYGJuamhgbGloYKdUCAOueVttcAAAA.tnb4rLBEHNhQPY_dRZQFJr598KEelfW0bNJUAsW2nOlrqkQaFjzwwaohcgHI0MVuS1944x2TAMMVaGOuEcQVvA",
    user: null,
    loginStatus: true
  },
  onLaunch: function () {
  },
  onShow: function () {
    console.log

    ('App Show')
  },
  onHide: function () {
    console.log

    ('App Hide')
  }
}
</script>
