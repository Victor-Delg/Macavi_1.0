import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prod-fact.reducer';

export const ProdFactDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const prodFactEntity = useAppSelector(state => state.prodFact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="prodFactDetailsHeading">
          <Translate contentKey="macaviApp.prodFact.detail.title">ProdFact</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{prodFactEntity.id}</dd>
          <dt>
            <Translate contentKey="macaviApp.prodFact.factura">Factura</Translate>
          </dt>
          <dd>{prodFactEntity.factura ? prodFactEntity.factura.id : ''}</dd>
          <dt>
            <Translate contentKey="macaviApp.prodFact.producto">Producto</Translate>
          </dt>
          <dd>{prodFactEntity.producto ? prodFactEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/prod-fact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prod-fact/${prodFactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProdFactDetail;
