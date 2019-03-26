<template>
    <b-container>
        <b-row>
            <b-btn v-b-modal.categoryEditor variant="success">Create Category</b-btn>
        </b-row>
        <hr>
        <b-row class="pt-4">
            <b-list-group>
                <b-list-group-item v-for="category in categories"
                                   style="min-width: 18rem"
                                   :href="generateLinkFor(category)"
                                   :key="category.id"
                                   class="d-flex justify-content-between align-items-center">
                    {{category.title}}
                    <b-badge variant="primary" pill>
                        {{category.itemCount}}
                    </b-badge>
                </b-list-group-item>
            </b-list-group>
        </b-row>
        <b-row>
            <b-modal id="categoryEditor"
                     ref="categoryEditor"
                     title="New Category"
                     ok-title="Save"
                     ok-variant="success"
                     :ok-disabled="!categoryTitle || !!categoryTitleErrorFeedback"
                     @ok="saveClicked"
                     @hidden="hideModal">
                <form>
                    <b-form-input type="text"
                                  @input="categoryTitleErrorFeedback = ''"
                                  placeholder="Category title"
                                  aria-describedby="categoryTitleErrorFeedback"
                                  :state="isCategoryTitleValid"
                                  v-model="categoryTitle">
                    </b-form-input>
                    <b-form-invalid-feedback id="categoryTitleErrorFeedback">
                        {{categoryTitleErrorFeedback}}
                    </b-form-invalid-feedback>
                </form>
            </b-modal>
        </b-row>
    </b-container>
</template>

<script>

    export default {
        name: 'categories',
        created() {
            this.loadCategories();
        },
        data() {
            return {
                categories: [],
                categoryTitle: '',
                categoryTitleErrorFeedback: ''
            }
        },
        computed: {
            isCategoryTitleValid() {
                if (this.categoryTitle && this.categoryTitleErrorFeedback) {
                    return false
                } else {
                    return undefined
                }
            }
        },
        methods: {
            loadCategories() {
                this.$http.get("api/category")
                    .then(response => {
                        this.categories = response.body
                    })
            },
            generateLinkFor(category) {
                return `#/items/${category.id}`
            },
            saveClicked(event) {
                event.preventDefault()
                this.$http.post("api/category", {title: this.categoryTitle})
                    .then(() => {
                            this.$refs.categoryEditor.hide()
                            this.loadCategories()
                        },
                        reason => {
                            this.categoryTitleErrorFeedback = reason.body.message
                        }
                    )
            },
            hideModal() {
                this.categoryTitleErrorFeedback = ''
                this.categoryTitle = ''
            }
        }
    }
</script>