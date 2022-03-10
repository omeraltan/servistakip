import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IProtokolTemel } from 'app/shared/model/protokol-temel.model';
import { getEntities as getProtokolTemels } from 'app/entities/protokol-temel/protokol-temel.reducer';
import { IKurumTemel } from 'app/shared/model/kurum-temel.model';
import { getEntities as getKurumTemels } from 'app/entities/kurum-temel/kurum-temel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './servis-temel.reducer';
import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ServisSekli } from 'app/shared/model/enumerations/servis-sekli.model';
import { ServisTipi } from 'app/shared/model/enumerations/servis-tipi.model';
import { BaglantiSekli } from 'app/shared/model/enumerations/baglanti-sekli.model';
import { ServisDurum } from 'app/shared/model/enumerations/servis-durum.model';

export const ServisTemelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const protokolTemels = useAppSelector(state => state.protokolTemel.entities);
  const kurumTemels = useAppSelector(state => state.kurumTemel.entities);
  const servisTemelEntity = useAppSelector(state => state.servisTemel.entity);
  const loading = useAppSelector(state => state.servisTemel.loading);
  const updating = useAppSelector(state => state.servisTemel.updating);
  const updateSuccess = useAppSelector(state => state.servisTemel.updateSuccess);
  const servisSekliValues = Object.keys(ServisSekli);
  const servisTipiValues = Object.keys(ServisTipi);
  const baglantiSekliValues = Object.keys(BaglantiSekli);
  const servisDurumValues = Object.keys(ServisDurum);
  const handleClose = () => {
    props.history.push('/servis-temel' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProtokolTemels({}));
    dispatch(getKurumTemels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...servisTemelEntity,
      ...values,
      protokol: protokolTemels.find(it => it.id.toString() === values.protokol.toString()),
      bakanlik: kurumTemels.find(it => it.id.toString() === values.bakanlik.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          servisSekli: 'KURUMDAN_ALINAN',
          servisTipi: 'WEB',
          baglantiSekli: 'ACIK_INTERNET',
          servisDurum: 'PLANLANAN',
          ...servisTemelEntity,
          protokol: servisTemelEntity?.protokol?.id,
          bakanlik: servisTemelEntity?.bakanlik?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.servisTemel.home.createOrEditLabel" data-cy="ServisTemelCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.servisTemel.home.createOrEditLabel">Create or edit a ServisTemel</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="servis-temel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.servisAdi')}
                id="servis-temel-servisAdi"
                name="servisAdi"
                data-cy="servisAdi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 250, message: translate('entity.validation.maxlength', { max: 250 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.servisUrl')}
                id="servis-temel-servisUrl"
                name="servisUrl"
                data-cy="servisUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.servisVeriTip')}
                id="servis-temel-servisVeriTip"
                name="servisVeriTip"
                data-cy="servisVeriTip"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.aciklamasi')}
                id="servis-temel-aciklamasi"
                name="aciklamasi"
                data-cy="aciklamasi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 500, message: translate('entity.validation.maxlength', { max: 500 }) },
                }}
              />
              <ValidatedBlobField
                label={translate('servisTakipApp.servisTemel.dosyasi')}
                id="servis-temel-dosyasi"
                name="dosyasi"
                data-cy="dosyasi"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('servisTakipApp.servisTemel.resmi')}
                id="servis-temel-resmi"
                name="resmi"
                data-cy="resmi"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.servisSekli')}
                id="servis-temel-servisSekli"
                name="servisSekli"
                data-cy="servisSekli"
                type="select"
              >
                {servisSekliValues.map(servisSekli => (
                  <option value={servisSekli} key={servisSekli}>
                    {translate('servisTakipApp.ServisSekli.' + servisSekli)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.servisTipi')}
                id="servis-temel-servisTipi"
                name="servisTipi"
                data-cy="servisTipi"
                type="select"
              >
                {servisTipiValues.map(servisTipi => (
                  <option value={servisTipi} key={servisTipi}>
                    {translate('servisTakipApp.ServisTipi.' + servisTipi)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.baglantiSekli')}
                id="servis-temel-baglantiSekli"
                name="baglantiSekli"
                data-cy="baglantiSekli"
                type="select"
              >
                {baglantiSekliValues.map(baglantiSekli => (
                  <option value={baglantiSekli} key={baglantiSekli}>
                    {translate('servisTakipApp.BaglantiSekli.' + baglantiSekli)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.servisDurum')}
                id="servis-temel-servisDurum"
                name="servisDurum"
                data-cy="servisDurum"
                type="select"
              >
                {servisDurumValues.map(servisDurum => (
                  <option value={servisDurum} key={servisDurum}>
                    {translate('servisTakipApp.ServisDurum.' + servisDurum)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.anlikSorgu')}
                id="servis-temel-anlikSorgu"
                name="anlikSorgu"
                data-cy="anlikSorgu"
                type="text"
              />
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.yiginVeri')}
                id="servis-temel-yiginVeri"
                name="yiginVeri"
                data-cy="yiginVeri"
                type="text"
              />
              <ValidatedField
                label={translate('servisTakipApp.servisTemel.gds')}
                id="servis-temel-gds"
                name="gds"
                data-cy="gds"
                type="text"
              />
              <ValidatedField
                id="servis-temel-protokol"
                name="protokol"
                data-cy="protokol"
                label={translate('servisTakipApp.servisTemel.protokol')}
                type="select"
              >
                <option value="" key="0" />
                {protokolTemels
                  ? protokolTemels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="servis-temel-bakanlik"
                name="bakanlik"
                data-cy="bakanlik"
                label={translate('servisTakipApp.servisTemel.bakanlik')}
                type="select"
              >
                <option value="" key="0" />
                {kurumTemels
                  ? kurumTemels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/servis-temel" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ServisTemelUpdate;
