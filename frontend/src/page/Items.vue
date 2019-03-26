<template>
    <b-container>
        <b-row>
            <b-col md="3">
                <b-form-select @change="categoryFilterChanged" :value="selectedCategory" :options="categoryOptions"
                               class="mb-3">
                    <template slot="first">
                        <option value="">All Categories</option>
                    </template>
                </b-form-select>
            </b-col>
            <b-col md="9">
                <b-btn v-b-modal.itemEditor variant="success">Add Item</b-btn>
            </b-col>
        </b-row>
        <hr>
        <b-row>
            <b-card-group deck v-for="i in cardGroupSize" :key="i">
                <b-card v-for="item in getItemsFor(i)"
                        :key="item.id"
                        border-variant="secondary"
                        :title="item.title"
                        class="mb-4"
                        style="min-height: 15rem; width: 15rem"
                        :footer="`Price: ${item.price}₺`"
                        footer-tag="footer"
                        footer-border-variant="dark">
                    <b-card-body>
                        <p class="card-text">
                            {{item.text}}
                        </p>
                    </b-card-body>
                </b-card>
            </b-card-group>
        </b-row>
        <b-row>
            <item-editor @saved="loadItems()" :category-options="categoryOptions"></item-editor>
        </b-row>
    </b-container>
</template>

<script>

    import ItemEditor from "../editor/ItemEditor.vue";

    export default {
        name: 'items',
        components: {ItemEditor},
        created() {
            this.loadItems()
            this.loadCategories()
        },
        data() {
            return {
                items: [],
                categories: []
            }
        },
        computed: {
            categoryOptions() {
                return this.categories
                    .map(category => {
                        return {
                            value: category.id,
                            text: category.title
                        }
                    });
            },
            selectedCategory() {
                return Number(this.$route.params.categoryId) || ''
            },
            cardGroupSize() {
                const length = this.items.length;
                return Math.ceil(length / 4)
            }
        },
        methods: {
            loadItems() {
                const apiUrl = `api/item?category=${this.selectedCategory}`;
                this.$http.get(apiUrl)
                    .then(response => {
                        this.items = response.body
                    })
            },
            loadCategories() {
                this.$http.get('api/category')
                    .then(response => {
                        this.categories = response.body
                    })
            },
            categoryFilterChanged(val) {
                this.$router.push(`/items/${val}`)
                this.loadItems()
            },
            getItemsFor(i) {
                return this.items.slice((i - 1) * 4, Math.min(i * 4, this.items.length));
            }
        }
    }
</script>