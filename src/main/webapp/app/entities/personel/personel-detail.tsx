import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './personel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personelEntity = useAppSelector(state => state.personel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personelDetailsHeading">
          <Translate contentKey="servisTakipApp.personel.detail.title">Personel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personelEntity.id}</dd>
          <dt>
            <span id="adi">
              <Translate contentKey="servisTakipApp.personel.adi">Adi</Translate>
            </span>
          </dt>
          <dd>{personelEntity.adi}</dd>
          <dt>
            <span id="soyadi">
              <Translate contentKey="servisTakipApp.personel.soyadi">Soyadi</Translate>
            </span>
          </dt>
          <dd>{personelEntity.soyadi}</dd>
          <dt>
            <span id="telefonu">
              <Translate contentKey="servisTakipApp.personel.telefonu">Telefonu</Translate>
            </span>
          </dt>
          <dd>{personelEntity.telefonu}</dd>
          <dt>
            <span id="eposta">
              <Translate contentKey="servisTakipApp.personel.eposta">Eposta</Translate>
            </span>
          </dt>
          <dd>{personelEntity.eposta}</dd>
          <dt>
            <span id="unvani">
              <Translate contentKey="servisTakipApp.personel.unvani">Unvani</Translate>
            </span>
          </dt>
          <dd>{personelEntity.unvani}</dd>
        </dl>
        <Button tag={Link} to="/personel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/personel/${personelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonelDetail;
