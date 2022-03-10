import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { getEntities as getServisTemels } from 'app/entities/servis-temel/servis-temel.reducer';
import { IPersonel } from 'app/shared/model/personel.model';
import { getEntities as getPersonels } from 'app/entities/personel/personel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './sorun-temel.reducer';
import { ISorunTemel } from 'app/shared/model/sorun-temel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SorunTemelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const servisTemels = useAppSelector(state => state.servisTemel.entities);
  const personels = useAppSelector(state => state.personel.entities);
  const sorunTemelEntity = useAppSelector(state => state.sorunTemel.entity);
  const loading = useAppSelector(state => state.sorunTemel.loading);
  const updating = useAppSelector(state => state.sorunTemel.updating);
  const updateSuccess = useAppSelector(state => state.sorunTemel.updateSuccess);
  const handleClose = () => {
    props.history.push('/sorun-temel' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getServisTemels({}));
    dispatch(getPersonels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sorunTemelEntity,
      ...values,
      servis: servisTemels.find(it => it.id.toString() === values.servis.toString()),
      bulan: personels.find(it => it.id.toString() === values.bulan.toString()),
      cozen: personels.find(it => it.id.toString() === values.cozen.toString()),
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
          ...sorunTemelEntity,
          servis: sorunTemelEntity?.servis?.id,
          bulan: sorunTemelEntity?.bulan?.id,
          cozen: sorunTemelEntity?.cozen?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.sorunTemel.home.createOrEditLabel" data-cy="SorunTemelCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.sorunTemel.home.createOrEditLabel">Create or edit a SorunTemel</Translate>
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
                  id="sorun-temel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.sorunTemel.sorunAciklamasi')}
                id="sorun-temel-sorunAciklamasi"
                name="sorunAciklamasi"
                data-cy="sorunAciklamasi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 500, message: translate('entity.validation.maxlength', { max: 500 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.sorunTemel.sorunTarihi')}
                id="sorun-temel-sorunTarihi"
                name="sorunTarihi"
                data-cy="sorunTarihi"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.sorunTemel.cozumAciklamasi')}
                id="sorun-temel-cozumAciklamasi"
                name="cozumAciklamasi"
                data-cy="cozumAciklamasi"
                type="text"
                validate={{
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 500, message: translate('entity.validation.maxlength', { max: 500 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.sorunTemel.cozumTarihi')}
                id="sorun-temel-cozumTarihi"
                name="cozumTarihi"
                data-cy="cozumTarihi"
                type="date"
              />
              <ValidatedField
                id="sorun-temel-servis"
                name="servis"
                data-cy="servis"
                label={translate('servisTakipApp.sorunTemel.servis')}
                type="select"
              >
                <option value="" key="0" />
                {servisTemels
                  ? servisTemels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="sorun-temel-bulan"
                name="bulan"
                data-cy="bulan"
                label={translate('servisTakipApp.sorunTemel.bulan')}
                type="select"
              >
                <option value="" key="0" />
                {personels
                  ? personels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="sorun-temel-cozen"
                name="cozen"
                data-cy="cozen"
                label={translate('servisTakipApp.sorunTemel.cozen')}
                type="select"
              >
                <option value="" key="0" />
                {personels
                  ? personels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sorun-temel" replace color="info">
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

export default SorunTemelUpdate;
