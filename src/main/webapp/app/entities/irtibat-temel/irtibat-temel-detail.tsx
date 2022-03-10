import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './irtibat-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IrtibatTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const irtibatTemelEntity = useAppSelector(state => state.irtibatTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="irtibatTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.irtibatTemel.detail.title">IrtibatTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{irtibatTemelEntity.id}</dd>
          <dt>
            <span id="adi">
              <Translate contentKey="servisTakipApp.irtibatTemel.adi">Adi</Translate>
            </span>
          </dt>
          <dd>{irtibatTemelEntity.adi}</dd>
          <dt>
            <span id="soyadi">
              <Translate contentKey="servisTakipApp.irtibatTemel.soyadi">Soyadi</Translate>
            </span>
          </dt>
          <dd>{irtibatTemelEntity.soyadi}</dd>
          <dt>
            <span id="telefonu">
              <Translate contentKey="servisTakipApp.irtibatTemel.telefonu">Telefonu</Translate>
            </span>
          </dt>
          <dd>{irtibatTemelEntity.telefonu}</dd>
          <dt>
            <span id="tcnu">
              <Translate contentKey="servisTakipApp.irtibatTemel.tcnu">Tcnu</Translate>
            </span>
          </dt>
          <dd>{irtibatTemelEntity.tcnu}</dd>
          <dt>
            <span id="eposta">
              <Translate contentKey="servisTakipApp.irtibatTemel.eposta">Eposta</Translate>
            </span>
          </dt>
          <dd>{irtibatTemelEntity.eposta}</dd>
          <dt>
            <span id="unvani">
              <Translate contentKey="servisTakipApp.irtibatTemel.unvani">Unvani</Translate>
            </span>
          </dt>
          <dd>{irtibatTemelEntity.unvani}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.irtibatTemel.kurum">Kurum</Translate>
          </dt>
          <dd>{irtibatTemelEntity.kurum ? irtibatTemelEntity.kurum.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/irtibat-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/irtibat-temel/${irtibatTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IrtibatTemelDetail;
