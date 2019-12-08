<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="arzt">
                <h2 class="jh-entity-heading"><span v-text="$t('pkvApp.arzt.detail.title')">Arzt</span> {{arzt.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('pkvApp.arzt.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{arzt.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.strasse')">Strasse</span>
                    </dt>
                    <dd>
                        <span>{{arzt.strasse}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.hausnummer')">Hausnummer</span>
                    </dt>
                    <dd>
                        <span>{{arzt.hausnummer}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.plz')">Plz</span>
                    </dt>
                    <dd>
                        <span>{{arzt.plz}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.ort')">Ort</span>
                    </dt>
                    <dd>
                        <span>{{arzt.ort}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.telefon')">Telefon</span>
                    </dt>
                    <dd>
                        <span>{{arzt.telefon}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.telefon2')">Telefon 2</span>
                    </dt>
                    <dd>
                        <span>{{arzt.telefon2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.fax')">Fax</span>
                    </dt>
                    <dd>
                        <span>{{arzt.fax}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.email')">Email</span>
                    </dt>
                    <dd>
                        <span>{{arzt.email}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('pkvApp.arzt.web')">Web</span>
                    </dt>
                    <dd>
                        <span>{{arzt.web}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="arzt.id" :to="{name: 'ArztEdit', params: {arztId: arzt.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./arzt-details.component.ts">
</script>
