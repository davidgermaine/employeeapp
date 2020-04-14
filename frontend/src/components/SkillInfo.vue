<template>
    <div class="skillinfo">
        <div id="skill-info">
            <div class="tiny-header">Skill: </div>  {{skill.field.name}} {{skill.field.type}}<br>
            <div class="tiny-header">Experience: </div>  {{skill.experience}} months<br>
            <div class="tiny-header">Description: </div>  {{skill.summary}}
        </div>
        <div id="edit-skill-button">
            <button v-on:click.prevent="edit"> Edit Skill </button>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        skill: Object
    },

    methods: {
        edit() {
            this.$emit('editThisSkill', this.skill.id);
        }
    }

}
</script>

<style>
    .skillinfo {
        border: 2px solid black;
        margin: 8px 0 8px 0;
        padding: 8px 8px 8px 8px;
        display: grid;
        grid-template-columns: 70% 20% 10%;
        grid-template-areas: "skill-info . edit-skill-button"
    }

    .tiny-header {
        font-weight: bold;
        display: inline-block;
    }

    #skill-info {
        display: inline-block;
        grid-area: skill-info
    }

    #edit-skill-button {
        display: inline-block;
        text-align: right;
        grid-area: edit-skill-button
    }

</style>