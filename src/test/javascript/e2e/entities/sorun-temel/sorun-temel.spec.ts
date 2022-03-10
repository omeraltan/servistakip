import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import SorunTemelComponentsPage from './sorun-temel.page-object';
import SorunTemelUpdatePage from './sorun-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('SorunTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sorunTemelComponentsPage: SorunTemelComponentsPage;
  let sorunTemelUpdatePage: SorunTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    sorunTemelComponentsPage = new SorunTemelComponentsPage();
    sorunTemelComponentsPage = await sorunTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load SorunTemels', async () => {
    expect(await sorunTemelComponentsPage.title.getText()).to.match(/Sorun Temels/);
    expect(await sorunTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete SorunTemels', async () => {
    const beforeRecordsCount = (await isVisible(sorunTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(sorunTemelComponentsPage.table);
    sorunTemelUpdatePage = await sorunTemelComponentsPage.goToCreateSorunTemel();
    await sorunTemelUpdatePage.enterData();
    expect(await isVisible(sorunTemelUpdatePage.saveButton)).to.be.false;

    expect(await sorunTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(sorunTemelComponentsPage.table);
    await waitUntilCount(sorunTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await sorunTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await sorunTemelComponentsPage.deleteSorunTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(sorunTemelComponentsPage.records, beforeRecordsCount);
      expect(await sorunTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(sorunTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
