<template>
    <b-modal id="itemEditor"
             ref="itemEditor"
             title="Add Item"
             ok-title="Save"
             ok-variant="success"
             :ok-disabled="isSaveButtonDisabled"
             @ok="saveClicked"
             @hidden="emptyFields">
        <b-form-group
                :label-cols="2"
                label-size="md"
                label="Title: ">
            <b-form-input type="text"
                          placeholder="Enter title"
                          v-model="model.title">
            </b-form-input>
        </b-form-group>
        <b-form-group
                :label-cols="2"
                label-size="md"
                label="Text: ">
            <b-form-input type="text"
                          placeholder="Enter text"
                          v-model="model.text">
            </b-form-input>
        </b-form-group>
        <b-form-group
                :label-cols="2"
                label-size="md"
                label="Price: ">
            <b-form-input type="number"
                          placeholder="Enter price in ₺"
                          v-model="model.price">
            </b-form-input>
        </b-form-group>
        <b-form-group
                :label-cols="2"
                label-size="md"
                label="Category: ">
            <b-form-select v-model="model.category" :options="categoryOptions" class="mb-3">
                <template slot="first">
                    <option value="" disabled>-- Select an item category --</option>
                </template>
            </b-form-select>
        </b-form-group>
    </b-modal>
</template>

<script>

    export default {
        name: 'item-editor',
        props: {
            categoryOptions: {
                type: Array,
                required: true
            }
        },
        data() {
            return {
                errorMessage: '',
                model: {
                    title: '',
                    text: '',
                    price: '',
                    category: ''
                }
            }
        },
        computed: {
            isSaveButtonDisabled() {
                const isFormValid = this.model.title
                    && this.model.text
                    && (this.model.price || this.model.price === 0)
                    && this.model.category;

                return !isFormValid;
            }
        },
        methods: {
            saveClicked(event) {
                event.preventDefault()
                this.$http.post("api/item", this.model)
                    .then(() => {
                            this.$refs.itemEditor.hide()
                            this.$emit('saved')
                        },
                        reason => {
                            this.errorMessage = reason.body.message
                        }
                    )
            },
            emptyFields() {
                this.model = {
                    title: '',
                    text: '',
                    price: '',
                    category: ''
                }
            }
        }
    }
</script>