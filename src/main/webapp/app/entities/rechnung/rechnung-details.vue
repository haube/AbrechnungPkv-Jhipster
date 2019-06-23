<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rechnung">
                <h2 class="jh-entity-heading"><span v-text="$t('pkvApp.rechnung.detail.title')">Rechnung</span> {{rechnung.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('pkvApp.rechnung.betrag')">Betrag</span>
                    </dt>
                    <dd>
                        <span>{{rechnung.betrag}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.rechnung.datumRechnung')">Datum Rechnung</span>
                    </dt>
                    <dd>
                        <span>{{rechnung.datumRechnung | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.rechnung.datumZahlung')">Datum Zahlung</span>
                    </dt>
                    <dd>
                        <span>{{rechnung.datumZahlung | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.rechnung.arzt')">Arzt</span>
                    </dt>
                    <dd>
                        <div v-if="rechnung.arzt">
                            <router-link :to="{name: 'ArztView', params: {arztId: rechnung.arzt.id}}">{{rechnung.arzt.name}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rechnung.id" :to="{name: 'RechnungEdit', params: {rechnungId: rechnung.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rechnung-details.component.ts">
</script>
