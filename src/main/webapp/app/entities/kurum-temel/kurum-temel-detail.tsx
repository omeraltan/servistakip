import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './kurum-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const KurumTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const kurumTemelEntity = useAppSelector(state => state.kurumTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kurumTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.kurumTemel.detail.title">KurumTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{kurumTemelEntity.id}</dd>
          <dt>
            <span id="bakanlikAdi">
              <Translate contentKey="servisTakipApp.kurumTemel.bakanlikAdi">Bakanlik Adi</Translate>
            </span>
          </dt>
          <dd>{kurumTemelEntity.bakanlikAdi}</dd>
          <dt>
            <span id="birimAdi">
              <Translate contentKey="servisTakipApp.kurumTemel.birimAdi">Birim Adi</Translate>
            </span>
          </dt>
          <dd>{kurumTemelEntity.birimAdi}</dd>
        </dl>
        <Button tag={Link} to="/kurum-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kurum-temel/${kurumTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default KurumTemelDetail;
