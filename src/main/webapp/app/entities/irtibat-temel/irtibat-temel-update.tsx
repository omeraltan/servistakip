import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IKurumTemel } from 'app/shared/model/kurum-temel.model';
import { getEntities as getKurumTemels } from 'app/entities/kurum-temel/kurum-temel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './irtibat-temel.reducer';
import { IIrtibatTemel } from 'app/shared/model/irtibat-temel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Unvani } from 'app/shared/model/enumerations/unvani.model';

export const IrtibatTemelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const kurumTemels = useAppSelector(state => state.kurumTemel.entities);
  const irtibatTemelEntity = useAppSelector(state => state.irtibatTemel.entity);
  const loading = useAppSelector(state => state.irtibatTemel.loading);
  const updating = useAppSelector(state => state.irtibatTemel.updating);
  const updateSuccess = useAppSelector(state => state.irtibatTemel.updateSuccess);
  const unvaniValues = Object.keys(Unvani);
  const handleClose = () => {
    props.history.push('/irtibat-temel' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getKurumTemels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...irtibatTemelEntity,
      ...values,
      kurum: kurumTemels.find(it => it.id.toString() === values.kurum.toString()),
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
          unvani: 'DEVLET_MEMURU',
          ...irtibatTemelEntity,
          kurum: irtibatTemelEntity?.kurum?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.irtibatTemel.home.createOrEditLabel" data-cy="IrtibatTemelCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.irtibatTemel.home.createOrEditLabel">Create or edit a IrtibatTemel</Translate>
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
                  id="irtibat-temel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.irtibatTemel.adi')}
                id="irtibat-temel-adi"
                name="adi"
                data-cy="adi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.irtibatTemel.soyadi')}
                id="irtibat-temel-soyadi"
                name="soyadi"
                data-cy="soyadi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.irtibatTemel.telefonu')}
                id="irtibat-temel-telefonu"
                name="telefonu"
                data-cy="telefonu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 15, message: translate('entity.validation.maxlength', { max: 15 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.irtibatTemel.tcnu')}
                id="irtibat-temel-tcnu"
                name="tcnu"
                data-cy="tcnu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.irtibatTemel.eposta')}
                id="irtibat-temel-eposta"
                name="eposta"
                data-cy="eposta"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.irtibatTemel.unvani')}
                id="irtibat-temel-unvani"
                name="unvani"
                data-cy="unvani"
                type="select"
              >
                {unvaniValues.map(unvani => (
                  <option value={unvani} key={unvani}>
                    {translate('servisTakipApp.Unvani.' + unvani)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="irtibat-temel-kurum"
                name="kurum"
                data-cy="kurum"
                label={translate('servisTakipApp.irtibatTemel.kurum')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/irtibat-temel" replace color="info">
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

export default IrtibatTemelUpdate;
