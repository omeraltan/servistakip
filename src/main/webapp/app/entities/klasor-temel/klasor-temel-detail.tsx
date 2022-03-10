import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './klasor-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const KlasorTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const klasorTemelEntity = useAppSelector(state => state.klasorTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="klasorTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.klasorTemel.detail.title">KlasorTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{klasorTemelEntity.id}</dd>
          <dt>
            <span id="odasi">
              <Translate contentKey="servisTakipApp.klasorTemel.odasi">Odasi</Translate>
            </span>
          </dt>
          <dd>{klasorTemelEntity.odasi}</dd>
          <dt>
            <span id="dolabi">
              <Translate contentKey="servisTakipApp.klasorTemel.dolabi">Dolabi</Translate>
            </span>
          </dt>
          <dd>{klasorTemelEntity.dolabi}</dd>
          <dt>
            <span id="klasoru">
              <Translate contentKey="servisTakipApp.klasorTemel.klasoru">Klasoru</Translate>
            </span>
          </dt>
          <dd>{klasorTemelEntity.klasoru}</dd>
          <dt>
            <span id="fihristSirasi">
              <Translate contentKey="servisTakipApp.klasorTemel.fihristSirasi">Fihrist Sirasi</Translate>
            </span>
          </dt>
          <dd>{klasorTemelEntity.fihristSirasi}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.klasorTemel.servis">Servis</Translate>
          </dt>
          <dd>{klasorTemelEntity.servis ? klasorTemelEntity.servis.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/klasor-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/klasor-temel/${klasorTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default KlasorTemelDetail;
