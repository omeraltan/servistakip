import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjeTemel from './proje-temel';
import ProjeTemelDetail from './proje-temel-detail';
import ProjeTemelUpdate from './proje-temel-update';
import ProjeTemelDeleteDialog from './proje-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjeTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjeTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjeTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjeTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjeTemelDeleteDialog} />
  </>
);

export default Routes;
