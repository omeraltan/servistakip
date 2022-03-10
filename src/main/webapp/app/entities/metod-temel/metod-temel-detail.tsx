import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './metod-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MetodTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const metodTemelEntity = useAppSelector(state => state.metodTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="metodTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.metodTemel.detail.title">MetodTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{metodTemelEntity.id}</dd>
          <dt>
            <span id="metodAdi">
              <Translate contentKey="servisTakipApp.metodTemel.metodAdi">Metod Adi</Translate>
            </span>
          </dt>
          <dd>{metodTemelEntity.metodAdi}</dd>
          <dt>
            <span id="metodNu">
              <Translate contentKey="servisTakipApp.metodTemel.metodNu">Metod Nu</Translate>
            </span>
          </dt>
          <dd>{metodTemelEntity.metodNu}</dd>
          <dt>
            <span id="metodAciklamasi">
              <Translate contentKey="servisTakipApp.metodTemel.metodAciklamasi">Metod Aciklamasi</Translate>
            </span>
          </dt>
          <dd>{metodTemelEntity.metodAciklamasi}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.metodTemel.servis">Servis</Translate>
          </dt>
          <dd>{metodTemelEntity.servis ? metodTemelEntity.servis.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/metod-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/metod-temel/${metodTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MetodTemelDetail;
