import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './protokol-temel.reducer';
import { IProtokolTemel } from 'app/shared/model/protokol-temel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { SureDurum } from 'app/shared/model/enumerations/sure-durum.model';

export const ProtokolTemelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const protokolTemelEntity = useAppSelector(state => state.protokolTemel.entity);
  const loading = useAppSelector(state => state.protokolTemel.loading);
  const updating = useAppSelector(state => state.protokolTemel.updating);
  const updateSuccess = useAppSelector(state => state.protokolTemel.updateSuccess);
  const sureDurumValues = Object.keys(SureDurum);
  const handleClose = () => {
    props.history.push('/protokol-temel' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...protokolTemelEntity,
      ...values,
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
          sureDurum: 'SURELI',
          ...protokolTemelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.protokolTemel.home.createOrEditLabel" data-cy="ProtokolTemelCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.protokolTemel.home.createOrEditLabel">Create or edit a ProtokolTemel</Translate>
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
                  id="protokol-temel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.protokolTemel.sureDurum')}
                id="protokol-temel-sureDurum"
                name="sureDurum"
                data-cy="sureDurum"
                type="select"
              >
                {sureDurumValues.map(sureDurum => (
                  <option value={sureDurum} key={sureDurum}>
                    {translate('servisTakipApp.SureDurum.' + sureDurum)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('servisTakipApp.protokolTemel.sureBitisTarihi')}
                id="protokol-temel-sureBitisTarihi"
                name="sureBitisTarihi"
                data-cy="sureBitisTarihi"
                type="date"
              />
              <ValidatedField
                label={translate('servisTakipApp.protokolTemel.protokolNu')}
                id="protokol-temel-protokolNu"
                name="protokolNu"
                data-cy="protokolNu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 250, message: translate('entity.validation.maxlength', { max: 250 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.protokolTemel.olurYaziNu')}
                id="protokol-temel-olurYaziNu"
                name="olurYaziNu"
                data-cy="olurYaziNu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 250, message: translate('entity.validation.maxlength', { max: 250 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.protokolTemel.protokolImzaTarihi')}
                id="protokol-temel-protokolImzaTarihi"
                name="protokolImzaTarihi"
                data-cy="protokolImzaTarihi"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.protokolTemel.protokolAciklamasi')}
                id="protokol-temel-protokolAciklamasi"
                name="protokolAciklamasi"
                data-cy="protokolAciklamasi"
                type="text"
                validate={{
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 500, message: translate('entity.validation.maxlength', { max: 500 }) },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/protokol-temel" replace color="info">
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

export default ProtokolTemelUpdate;
