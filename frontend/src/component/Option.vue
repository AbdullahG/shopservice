<template>
    <span>
        <b-button type="button" :pressed.sync="checked" variant="outline-secondary" class="btn btn-circle">
            {{type}}
        </b-button>
        {{text}}
    </span>
</template>

<script>

    export default {
        name: 'question-option',
        props: {
            type: {
                type: String,
                required: true
            },
            text: {
                type: String,
                required: true
            }
        },
        data() {
            return {
                state: false
            }
        },
        computed: {
            checked: {
                set(val) {
                    this.state = val
                    this.$emit("update:checked", val)
                },
                get() {
                    return this.state
                }
            }
        }
    }
</script>

<style scoped>
    .btn-circle {
        width: 30px;
        height: 30px;
        border-radius: 15px;
        text-align: center;
        font-size: 12px;
        font-weight: bold;
        line-height: 1.42857;
    }

    btn:focus {
        outline: none;
    }
</style>