<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="termin">
                <h2 class="jh-entity-heading"><span v-text="$t('pkvApp.termin.detail.title')">Termin</span> {{termin.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('pkvApp.termin.datum')">Datum</span>
                    </dt>
                    <dd>
                        <span>{{termin.datum | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.termin.notiz')">Notiz</span>
                    </dt>
                    <dd>
                        <span>{{termin.notiz}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.termin.arzt')">Arzt</span>
                    </dt>
                    <dd>
                        <div v-if="termin.arzt">
                            <router-link :to="{name: 'ArztView', params: {arztId: termin.arzt.id}}">{{termin.arzt.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="termin.id" :to="{name: 'TerminEdit', params: {terminId: termin.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./termin-details.component.ts">
</script>
