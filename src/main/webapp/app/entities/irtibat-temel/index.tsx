import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IrtibatTemel from './irtibat-temel';
import IrtibatTemelDetail from './irtibat-temel-detail';
import IrtibatTemelUpdate from './irtibat-temel-update';
import IrtibatTemelDeleteDialog from './irtibat-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IrtibatTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IrtibatTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IrtibatTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={IrtibatTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IrtibatTemelDeleteDialog} />
  </>
);

export default Routes;
